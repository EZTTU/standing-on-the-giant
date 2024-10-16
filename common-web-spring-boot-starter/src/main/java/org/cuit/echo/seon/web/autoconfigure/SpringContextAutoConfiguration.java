package org.cuit.echo.seon.web.autoconfigure;

import org.cuit.echo.seon.commonweb.utils.SpringContextUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Import;

/**
 * @author EtyMx
 * @date 2022/8/29
 */
@Import(SpringContextUtils.class)
@AutoConfiguration
@AutoConfigureOrder(Integer.MIN_VALUE)
public class SpringContextAutoConfiguration {
}
