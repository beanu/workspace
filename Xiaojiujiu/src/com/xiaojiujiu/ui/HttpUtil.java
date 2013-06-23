package com.xiaojiujiu.ui;

import java.io.IOException;

import com.beanu.arad.error.AradException;
import com.beanu.arad.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public class HttpUtil {

	public static JsonNode handleResult(String result) throws AradException {

		try {

			JsonNode node = JsonUtil.json2node(result);
			String statue = node.findValue("resCode").asText();
			if (statue != null && statue.equals("1")) {
				return node;
			} else {
				AradException e = new AradException();
				e.setError_code(statue);
				throw e;
			}

		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
			AradException exception = new AradException(e1.getMessage());
			throw exception;
		} catch (IOException e1) {
			e1.printStackTrace();
			AradException exception = new AradException(e1.getMessage());
			throw exception;
		} finally {

		}
	}
}
