package com.junior.money.api.event.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.junior.money.api.event.CreatedResourceEvent;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class CreateResourceListener implements ApplicationListener<CreatedResourceEvent> {

    @Override
    public void onApplicationEvent(CreatedResourceEvent event) {

        HttpServletResponse response = event.getResponse();
        Long code = event.getCode();

        String location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(code).toUriString();
        response.setHeader("Location", location);
    }

}
