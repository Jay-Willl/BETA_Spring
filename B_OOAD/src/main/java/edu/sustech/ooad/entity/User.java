package edu.sustech.ooad.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    Integer user_id;

    String user_name;

    String user_passwd;

    Integer user_gold;

    String user_email;

    String user_role;

}
