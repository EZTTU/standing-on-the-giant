package org.cuit.echo.seon.commonweb.enumeration;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(BaseEnum.class)
public class BaseEnumHandler<E extends Enum<E> & BaseEnum> extends BaseTypeHandler<E> {
    private final Class<E> type;

    public BaseEnumHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.code());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        }
        return codeOf(type, code);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        }
        return codeOf(type, code);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        }
        return codeOf(type, code);
    }

    private E codeOf(Class<E> enumType, int code) {
        for (E enumConstant : enumType.getEnumConstants()) {
            if (enumConstant.code().equals(code)) {
                return enumConstant;
            }
        }
        throw new IllegalArgumentException("Unknown enum code: " + code);
    }
}
