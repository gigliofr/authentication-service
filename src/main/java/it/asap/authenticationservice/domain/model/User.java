package it.asap.authenticationservice.domain.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
public class User {
    private String username;
    private String email;
    private String password;
    @CreatedDate
    private Date creationDate;
    @LastModifiedDate
    private Date lastUpdateDate;


}
