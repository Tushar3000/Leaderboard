package com.leaderboard;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class UserInfo {
    
    private int id;
    private String username;
    private String password;
    private String name;
    private String role;

}
