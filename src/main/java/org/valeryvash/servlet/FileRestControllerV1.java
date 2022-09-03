package org.valeryvash.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.valeryvash.dto.APIResponse;
import org.valeryvash.dto.FilePostRequestDto;
import org.valeryvash.dto.FilePostResponseDto;
import org.valeryvash.dto.UserPostResponseDto;
import org.valeryvash.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.valeryvash.util.ServletHelper.jsonResponsePrinter;
import static org.valeryvash.util.ServletHelper.stringRequestReader;


@WebServlet(name = "FileRestControllerV1", value = "/api/v1/files/*")
public class FileRestControllerV1 extends HttpServlet {

    private final String servletUrlPattern = "/api/v1/files/";
    private final FileService fileService;

    private final Gson gson;

    public FileRestControllerV1() {
        this.fileService = new FileService();
        this.gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonRequest = stringRequestReader(request);
        try{
            FilePostRequestDto requestDto = gson.fromJson(jsonRequest, FilePostRequestDto.class);
            FilePostResponseDto responseDto = fileService.add(requestDto);
            String jsonResponse = gson.toJson(responseDto);
            jsonResponsePrinter(
                    response,
                    new APIResponse(201, jsonResponse)
            );

        } catch (JsonSyntaxException e) {
            jsonResponsePrinter(
                    response,
                    new APIResponse(405, "Invalid data")
            );
        } catch (IllegalArgumentException e) {
            jsonResponsePrinter(
                    response,
                    new APIResponse(400, "Invalid id supplied")
            );
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestUri = request.getRequestURI();

        int index = requestUri.indexOf(servletUrlPattern) + servletUrlPattern.length();
        String substring = requestUri.substring(index);

        if (!substring.equals("")) {
            try {
                Long id = Long.valueOf(substring);
                FilePostResponseDto userDto = fileService.get(id);
                String jsonUserResponse = gson.toJson(userDto);
                jsonResponsePrinter(
                        response,
                        new APIResponse(200, jsonUserResponse));
            } catch (IllegalArgumentException e) {
                jsonResponsePrinter(
                        response,
                        new APIResponse(400, "Invalid id supplied"));
            } catch (RuntimeException e) {
                jsonResponsePrinter(
                        response,
                        new APIResponse(404, "File not found")
                );
            }
        } else {
            List<FilePostResponseDto> responseDtos = fileService.getAll();
            String jsonResponseDtos = gson.toJson(responseDtos);
            jsonResponsePrinter(
                    response,
                    new APIResponse(200, jsonResponseDtos)
            );
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonRequest = stringRequestReader(request);

        try {
            FilePostResponseDto responseDto = gson.fromJson(jsonRequest, FilePostResponseDto .class);
            responseDto  = fileService.update(responseDto);
            String jsonResponse = gson.toJson(responseDto);
            jsonResponsePrinter(
                    response,
                    new APIResponse(200, jsonResponse)
            );
        } catch (JsonSyntaxException e) {
            jsonResponsePrinter(
                    response,
                    new APIResponse(405, "Invalid data")
            );
        } catch (IllegalArgumentException e) {
            jsonResponsePrinter(
                    response,
                    new APIResponse(400, "Invalid id supplied")
            );
        } catch (RuntimeException e) {
            jsonResponsePrinter(
                    response,
                    new APIResponse(404, "File not found")
            );
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestUri = request.getRequestURI();

        int index = requestUri.indexOf(servletUrlPattern) + servletUrlPattern.length();
        String substring = requestUri.substring(index);

        if (!substring.equals("")) {
            try{
                Long id = Long.valueOf(substring);
                FilePostResponseDto fileDto = fileService.remove(id);
                String jsonResponse = gson.toJson(fileDto);
                jsonResponsePrinter(
                        response,
                        new APIResponse(200, jsonResponse)
                );
            } catch (IllegalArgumentException e) {
                jsonResponsePrinter(
                        response,
                        new APIResponse(400, "Invalid id supplied")
                );
            } catch (RuntimeException e) {
                response.setStatus(404);
                jsonResponsePrinter(
                        response,
                        new APIResponse(404, "File not found")
                );
            }
        } else {
            jsonResponsePrinter(
                    response,
                    new APIResponse(400, "Invalid id supplied")
            );
        }
    }


}
