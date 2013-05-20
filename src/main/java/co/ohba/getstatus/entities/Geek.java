package co.ohba.getstatus.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA.
 * User: andre
 * Date: 5/9/13
 * Time: 9:46 PM
 * To change this template use File | Settings | File Templates.
 */
@Data
@Entity
public class Geek {

    @Id
    private Long id;

    private String name, username, email, password;

}
