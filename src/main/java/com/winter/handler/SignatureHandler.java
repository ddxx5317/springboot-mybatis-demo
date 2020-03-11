package com.winter.handler;

import com.alibaba.fastjson.JSON;
import com.winter.utils.KeyFactory;
import com.winter.utils.Signature;
import com.winter.utils.StringUtils;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by suye on 2017/7/17.
 */
public class SignatureHandler extends BaseTypeHandler<Signature> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Signature parameter, JdbcType jdbcType) throws SQLException {
        if (null == parameter)
            ps.setString(i, "{}");

        Map<String, String> map = KeyFactory.keyFactory().signature2map(parameter);
        ps.setString(i, JSON.toJSONString(map));
    }

    @SuppressWarnings("unchecked")
	@Override
    public Signature getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String result = rs.getString(columnName);
        if (StringUtils.isEmpty(result)) return null;

        Map<String, String> map = JSON.parseObject(result, Map.class);
        return KeyFactory.keyFactory().map2signature(map);
    }

    @SuppressWarnings("unchecked")
	@Override
    public Signature getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String result = rs.getString(columnIndex);
        if (StringUtils.isEmpty(result)) return null;

        Map<String, String> map = JSON.parseObject(result, Map.class);
        return KeyFactory.keyFactory().map2signature(map);
    }

    @SuppressWarnings("unchecked")
	@Override
    public Signature getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String result = cs.getString(columnIndex);
        if (StringUtils.isEmpty(result)) return null;

        Map<String, String> map = JSON.parseObject(result, Map.class);
        return KeyFactory.keyFactory().map2signature(map);
    }
}
