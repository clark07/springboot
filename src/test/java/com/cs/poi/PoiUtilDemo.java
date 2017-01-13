package com.cs.poi;

import org.apache.poi.ss.formula.eval.FunctionEval;
import org.apache.poi.ss.util.WorkbookUtil;

import java.util.stream.Collectors;

/**
 * Created by admin on 2017/1/11.
 */
public class PoiUtilDemo {


	    public static void main(String[] args){

			String safeName = WorkbookUtil.createSafeSheetName("[O'Brien's sales*?]", '_'); // returns " O'Brien's sales   "
			System.out.println(safeName);
			System.out.println("---SupportedFunctionNames---");
			String supportedFunctionNames = FunctionEval.getSupportedFunctionNames().stream().collect(Collectors.joining(","));
			System.out.println(supportedFunctionNames);
			System.out.println("---NotSupportedFunctionNames---");
			String notSupportedFunctionNames = FunctionEval.getNotSupportedFunctionNames().stream().collect(Collectors.joining(","));
			System.out.println(notSupportedFunctionNames);



		}
}
