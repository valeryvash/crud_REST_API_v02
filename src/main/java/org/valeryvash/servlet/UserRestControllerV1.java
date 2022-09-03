package org.valeryvash.servlet;

import com.google.gson.Gson;
import org.valeryvash.dto.APIResponse;
import org.valeryvash.dto.UserPostRequestDto;
import org.valeryvash.dto.UserPostResponseDto;
import org.valeryvash.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.valeryvash.util.ServletHelper.jsonResponsePrinter;
import static org.valeryvash.util.ServletHelper.stringRequestReader;

@WebServlet(name = "UserRestControllerV1", value = "/api/v1/users/*")
public class UserRestControllerV1 extends HttpServlet {

    private final String servletUrlPattern = "/api/v1/users/";
    private final UserService userService;

    private final Gson gson;

    public UserRestControllerV1() {
        this.userService = new UserService();
        this.gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonRequest = stringRequestReader(request);

        UserPostRequestDto userPostRequestDto = gson.fromJson(jsonRequest, UserPostRequestDto.class);

        UserPostResponseDto userPostResponseDto = userService.add(userPostRequestDto);

        String jsonResponse = gson.toJson(userPostResponseDto);

        jsonResponsePrinter(response, new APIResponse(200, jsonResponse));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestUri = request.getRequestURI();

        int index = requestUri.indexOf(servletUrlPattern) + servletUrlPattern.length();
        String substring = requestUri.substring(index);

        if (!substring.equals("")) {
            try {
                Long id = Long.valueOf(substring);
                UserPostResponseDto userDto = userService.get(id);
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
                        new APIResponse(404, "User not found")
                );
            }
        } else {
            List<UserPostResponseDto> responseDtos = userService.getAll();
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

        UserPostResponseDto requestDto = gson.fromJson(jsonRequest, UserPostResponseDto.class);

        requestDto = userService.update(requestDto);

        String jsonResponse = gson.toJson(requestDto);

        jsonResponsePrinter(
                response,
                new APIResponse(200,jsonResponse)
        );
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestUri = request.getRequestURI();

        int index = requestUri.indexOf(servletUrlPattern) + servletUrlPattern.length();
        String substring = requestUri.substring(index);

        if (!substring.equals("")) {
            try {
                Long id = Long.valueOf(substring);
                UserPostResponseDto userDto = userService.remove(id);
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
                        new APIResponse(404, "User not found")
                );
            }
        } else {
            jsonResponsePrinter(
                    response,
                    new APIResponse(400, "Invalid id supplied"));
        }
    }
}
