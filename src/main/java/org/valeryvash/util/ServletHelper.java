package org.valeryvash.util;

import org.valeryvash.dto.APIResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class ServletHelper {

    public static String stringRequestReader(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = request.getReader()) {
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw ioe;
        }
        return sb.toString();
    }

    public static void jsonResponsePrinter(HttpServletResponse response, APIResponse apiResponse) throws IOException {
        response.setStatus(apiResponse.getCode());
        response.setContentType("application/json");
        response.getOutputStream().println(apiResponse.getMessage());
    }
}
