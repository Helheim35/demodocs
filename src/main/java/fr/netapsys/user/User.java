package fr.netapsys.user;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Ma classe utilisateur
 */
@Data
@Builder
public class User {

    /**
     * Identifiant
     */
    @NotNull
    private long id;
    /**
     * Prénom
     */
    @Size(max = 50)
    private String firstName;
    /**
     * Nom
     */
    @Size(max = 50)
    private String lastName;
    /**
     * Date de naissance
     */
    private Date birthday;
    /**
     * Etat du compte
     */
    private boolean locked;

}
