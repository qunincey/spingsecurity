/**
 * 
 */
package com.qunincey.security.core.properties;

import lombok.Data;

/**
 * @author zhailiang
 *
 */
@Data
public class OAuth2Properties {
	
	/**
	 * 使用jwt时为token签名的秘钥
	 */
	private String jwtSigningKey = "imooc";
	/**
	 * 客户端配置
	 */
	private OAuth2ClientProperties[] clients = {};


	
}
