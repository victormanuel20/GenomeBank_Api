
DROP DATABASE genomebank;
CREATE DATABASE genomebank;
USE genomebank;



CREATE TABLE species (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,          
    scientific_name VARCHAR(150) NOT NULL,         
    common_name VARCHAR(150)                       
);


CREATE TABLE genome (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,         
    version VARCHAR(50) NOT NULL,                 
    species_id BIGINT NOT NULL,                  
    FOREIGN KEY (species_id) REFERENCES species(id)
);


CREATE TABLE chromosome (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,          
    name VARCHAR(20) NOT NULL,                     
    length INT NOT NULL,                          
    sequence LONGTEXT NOT NULL,                   
    genome_id BIGINT NOT NULL,                    
    FOREIGN KEY (genome_id) REFERENCES genome(id)
);


CREATE TABLE gene (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,         
    symbol VARCHAR(50) NOT NULL,                 
    start_position INT NOT NULL,                   
    end_position INT NOT NULL,                    
    strand CHAR(1) NOT NULL,                      
    sequence TEXT NOT NULL,                       
    chromosome_id BIGINT NOT NULL,                
    FOREIGN KEY (chromosome_id) REFERENCES chromosome(id)
);


CREATE TABLE `function` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(20) NOT NULL,
    name VARCHAR(200) NOT NULL,
    category VARCHAR(10) NOT NULL
);


CREATE TABLE gene_function (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,        
    gene_id BIGINT NOT NULL,                      
    function_id BIGINT NOT NULL,                 
    evidence VARCHAR(50),                        
    FOREIGN KEY (gene_id) REFERENCES gene(id),
    FOREIGN KEY (function_id) REFERENCES `function`(id)
);


SHOW TABLES;
