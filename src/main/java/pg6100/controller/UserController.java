package pg6100.controller;

import pg6100.entity.UserData;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UserController implements Serializable{

    private UserData userData;

}
