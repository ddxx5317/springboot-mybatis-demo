package com.winter.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by suye on 2017/6/12.
 */
public class JSONHandler extends BaseTypeHandler<JSONObject> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JSONObject parameter, JdbcType jdbcType) throws SQLException {
        if(null != parameter) {
            ps.setString(i, parameter.toJSONString());
        }else{
            ps.setString(i, "");
        }
    }

    @Override
    public JSONObject getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.wasNull()) {
            return new JSONObject();
        } else {
            String result = rs.getString(columnName);

            if(StringUtils.isBlank(result)){
                return new JSONObject();
            }else{
                return JSON.parseObject(result);
            }
        }
    }

    @Override
    public JSONObject getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.wasNull()) {
            return new JSONObject();
        } else {
            String result = rs.getString(columnIndex);

            if(StringUtils.isBlank(result)){
                return new JSONObject();
            }else{
                return JSON.parseObject(result);
            }
        }
    }

    @Override
    public JSONObject getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.wasNull()) {
            return new JSONObject();
        } else {
            String result = cs.getString(columnIndex);

            if(StringUtils.isBlank(result)){
                return new JSONObject();
            }else{
                return JSON.parseObject(result);
            }
        }
    }

}
