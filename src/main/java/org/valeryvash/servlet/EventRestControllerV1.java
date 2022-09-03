package org.valeryvash.servlet;

import com.google.gson.Gson;
import org.valeryvash.dto.APIResponse;
import org.valeryvash.dto.EventPostResponseDto;
import org.valeryvash.service.EventService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.valeryvash.util.ServletHelper.jsonResponsePrinter;


@WebServlet(name = "EventRestControllerV1", value = "/api/v1/events/*")
public class EventRestControllerV1 extends HttpServlet {

    private final String servletUrlPattern = "/api/v1/events/";
    private final EventService eventService;

    private final Gson gson;

    public EventRestControllerV1() {
        this.eventService = new EventService();
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestUri = request.getRequestURI();

        int index = requestUri.indexOf(servletUrlPattern) + servletUrlPattern.length();
        String substring = requestUri.substring(index);

        if (!substring.equals("")) {
            try {
                Long id = Long.valueOf(substring);
                EventPostResponseDto postResponseDto = eventService.get(id);
                String jsonUserResponse = gson.toJson(postResponseDto);
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
                        new APIResponse(404, "Event not found")
                );
            }
        } else {
            List<EventPostResponseDto> postResponseDtos = eventService.getAll();
            String jsonResponseDtos = gson.toJson(postResponseDtos);
            jsonResponsePrinter(
                    response,
                    new APIResponse(200, jsonResponseDtos)
            );
        }
    }
}
