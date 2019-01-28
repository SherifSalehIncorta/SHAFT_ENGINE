package com.shaft.io;

import java.io.FileReader;
import java.io.IOException;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.testng.Assert;
import io.restassured.response.Response;

@Deprecated
public class JsonFileManager {

    public static boolean compareTypically(Response response, String jsFilePath) {

	JSONParser parser = new JSONParser();
	JSONObject expectedJsonObject;
	JSONObject actualJsonObject;
	try {
	    expectedJsonObject = (JSONObject) parser.parse(new FileReader(jsFilePath));
	    actualJsonObject = (JSONObject) parser.parse(response.asString());
	    return expectedJsonObject.equals(actualJsonObject);
	} catch (IOException e) {
	    ReportManager.log(e);
	    ReportManager.log("Couldn't find the desired file. [" + jsFilePath + "].");
	    Assert.fail("Couldn't find the desired file. [" + jsFilePath + "].");
	    return false;
	} catch (ParseException e) {
	    ReportManager.log(e);
	    return false;
	}
    }

    public static boolean compareStrictly(Response response, String jsFilePath) {
	JSONParser parser = new JSONParser();
	JSONObject expectedJsonObject;
	JSONObject actualJsonObject;
	JSONCompareResult result = null;

	try {
	    expectedJsonObject = (JSONObject) parser.parse(new FileReader(jsFilePath));
	    actualJsonObject = (JSONObject) parser.parse(response.asString());
	    result = JSONCompare.compareJSON(actualJsonObject.toJSONString(), expectedJsonObject.toJSONString(),
		    JSONCompareMode.STRICT);
	} catch (JSONException e) {
	    ReportManager.log(e);
	} catch (IOException e) {
	    ReportManager.log(e);
	    ReportManager.log("Couldn't find the desired file. [" + jsFilePath + "].");
	    Assert.fail("Couldn't find the desired file. [" + jsFilePath + "].");
	} catch (ParseException e) {
	    ReportManager.log(e);
	}

	if (result != null) {
	    return result.passed();
	} else {
	    return false;
	}
    }

    public static boolean compareNonStrictly(Response response, String jsFilePath) {
	JSONParser parser = new JSONParser();
	JSONObject expectedJsonObject;
	JSONObject actualJsonObject;
	JSONCompareResult result = null;

	try {
	    expectedJsonObject = (JSONObject) parser.parse(new FileReader(jsFilePath));
	    actualJsonObject = (JSONObject) parser.parse(response.asString());
	    result = JSONCompare.compareJSON(actualJsonObject.toJSONString(), expectedJsonObject.toJSONString(),
		    JSONCompareMode.NON_EXTENSIBLE);
	} catch (IOException e) {
	    ReportManager.log(e);
	    ReportManager.log("Couldn't find the desired file. [" + jsFilePath + "].");
	    Assert.fail("Couldn't find the desired file. [" + jsFilePath + "].");
	} catch (ParseException e) {
	    ReportManager.log(e);
	} catch (JSONException e) {
	    ReportManager.log(e);
	}

	if (result != null) {
	    return result.passed();
	} else {
	    return false;
	}
    }

    public static boolean containElements(Response response, String jsFilePath) {
	JSONParser parser = new JSONParser();
	JSONObject expectedJsonObject;
	JSONObject actualJsonObject;
	JSONCompareResult result = null;
	try {
	    expectedJsonObject = (JSONObject) parser.parse(new FileReader(jsFilePath));
	    actualJsonObject = (JSONObject) parser.parse(response.asString());
	    result = JSONCompare.compareJSON(expectedJsonObject.toJSONString(), actualJsonObject.toJSONString(),
		    JSONCompareMode.LENIENT);
	} catch (IOException e) {
	    ReportManager.log(e);
	    ReportManager.log("Couldn't find the desired file. [" + jsFilePath + "].");
	    Assert.fail("Couldn't find the desired file. [" + jsFilePath + "].");
	} catch (ParseException e) {
	    ReportManager.log(e);
	} catch (JSONException e) {
	    ReportManager.log(e);
	}

	if (result != null) {
	    return result.passed();
	} else {
	    return false;
	}
    }
}
