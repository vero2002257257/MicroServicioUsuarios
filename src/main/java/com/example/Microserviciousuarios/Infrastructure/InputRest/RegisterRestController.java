package com.example.Microserviciousuarios.Infrastructure.InputRest;

import com.example.Microserviciousuarios.Application.Dto.Request.RegisterRequest;
import com.example.Microserviciousuarios.Application.Dto.Response.RegisterResponse;
import com.example.Microserviciousuarios.Application.Handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.example.Microserviciousuarios.Utils.Constants.USER_CREATED_SUCCESSFULLY;
import static com.example.Microserviciousuarios.Utils.Constants.INVALID_PERMISSION;

@RestController
@RequestMapping("/register")
@Tag(name = "User Controller", description = "Operations related to user management")
@RequiredArgsConstructor
public class RegisterRestController {
    private final IUserHandler userHandler;

    @Operation(summary = "Crear nuevo usuario",
            description = "Crea un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = USER_CREATED_SUCCESSFULLY,
                    content = @Content(schema = @Schema(implementation = RegisterResponse.class))),
            @ApiResponse(responseCode = "403", description = INVALID_PERMISSION,
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<RegisterResponse> register(@RequestHeader(value = "Authorization", required = false)
                                                     @RequestBody RegisterRequest registerRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new RegisterResponse(INVALID_PERMISSION));
        }
        userHandler.createUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse(USER_CREATED_SUCCESSFULLY));


    }

}
