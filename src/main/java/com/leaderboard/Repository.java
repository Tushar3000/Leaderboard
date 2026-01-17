package com.leaderboard;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
 

public interface Repository extends JpaRepository<UserInfo,Integer>  {
    Optional<UserInfo> findByUsername(String username);
    
}
