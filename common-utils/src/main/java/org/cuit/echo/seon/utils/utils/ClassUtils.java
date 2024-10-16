package org.cuit.echo.seon.utils.utils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author EtyMx
 * @date 2021/4/30
 */
public class ClassUtils {

    /**
     * 获取指定包名下，继承接口的类
     *
     * @param packageName 包名
     * @param clazz       接口
     * @return 继承接口的类
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Set<Class<?>> getByInterface(String packageName, Class clazz) {
        Set<Class<?>> classesAll = getClasses(packageName);
        Set<Class<?>> classes = new LinkedHashSet<>();
        if (clazz.isInterface()) {
            try {
                for (Class<?> cls : classesAll) {
                    if (clazz.isAssignableFrom(cls)) {
                        if (!clazz.equals(cls)) {
                            //自身并不加进去
                            classes.add(cls);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return classes;
    }

    /**
     * 递归获取指定包下的类
     *
     * @param packageName 包名
     * @return 指定包下的类
     */
    public static Set<Class<?>> getClasses(String packageName) {
        return getClasses(packageName, true);
    }

    /**
     * 获取指定包下的类
     *
     * @param packageName 包名
     * @param recursive   是否递归
     * @return 指定包下的类
     */
    public static Set<Class<?>> getClasses(String packageName, boolean recursive) {
        Set<Class<?>> classes = new LinkedHashSet<>();
        // 获取包的名字并进行替换
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) { // 处理文件
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) { //处理jar包
                    JarFile jar;
                    try {
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        Enumeration<JarEntry> entries = jar.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            if (name.charAt(0) == '/') {
                                name = name.substring(1);
                            }
                            if (!name.startsWith(packageDirName)) {
                                continue;
                            }

                            int idx = name.lastIndexOf('/');
                            if (idx != -1 && recursive) { // 如果以"/"结尾则是一个包
                                packageName = name.substring(0, idx).replace('/', '.');
                                if (!entry.isDirectory() && name.endsWith(".class")) {
                                    String className = name.substring(packageName.length() + 1, name.length() - 6);
                                    try {
                                        classes.add(loadClass(packageName + '.' + className));
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName 包名
     * @param packagePath 包路径
     * @param recursive   是否递归
     * @param classes     类集合
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath,
                                                        final boolean recursive, Set<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            // log.warn("用户定义包名 " + packageName + " 下没有任何文件");
            return;
        }
        File[] dirFiles = dir.listFiles(file ->
            (recursive && file.isDirectory()) || (file.getName().endsWith(".class")));
        // 循环所有文件
        for (File file : dirFiles) {
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                    file.getAbsolutePath(), recursive, classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    classes.add(loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 加载类
     *
     * @param className 全类名
     * @return 类
     * @throws ClassNotFoundException
     */
    private static Class<?> loadClass(String className) throws ClassNotFoundException {
        return Thread.currentThread().getContextClassLoader().loadClass(className);
    }
}
