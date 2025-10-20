USE genomebank;


CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,            
    email VARCHAR(150) NOT NULL UNIQUE,               
    password VARCHAR(255) NOT NULL,                    
    active BOOLEAN DEFAULT TRUE,                      
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP    
);


CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE                  
);


CREATE TABLE user_role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);
