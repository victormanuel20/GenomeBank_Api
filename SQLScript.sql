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

USE genomebank;


INSERT INTO species (scientific_name, common_name)
VALUES 
('Homo sapiens', 'Human'),
('Mus musculus', 'Mouse');


INSERT INTO genome (version, species_id)
VALUES 
('GRCh38', 1),  
('GRCm39', 2);   


INSERT INTO chromosome (name, length, sequence, genome_id)
VALUES
('1', 60, 'ATGCGTACGTTAGCTAGCTAGGCTAACCGTTAGGCTAGCTAGCATCGATCGATGCTAGCTAG', 1),
('2', 55, 'GCTAGCTAGGCTAGCATCGATCGTAGCTAGGCTAGCATCGATGCTAGCTAGCTAGCTA', 1),
('X', 50, 'TACGATCGTAGCTAGCTAGGCTAACCGTTAGCATCGATGCTAGCTAGCTAG', 2);


INSERT INTO gene (symbol, start_position, end_position, strand, sequence, chromosome_id)
VALUES
('BRCA1', 5, 25, '+', 'CGTACGTTAGCTAGCTAGGCT', 1),
('TP53', 30, 50, '-', 'GCTAACCGTTAGGCTAGCTAG', 1),
('MYC', 10, 30, '+', 'TAGCATCGATCGTAGCTAGGCT', 2),
('ACTB', 15, 40, '+', 'CGTAGCTAGCATCGATGCTAGCTAG', 2),
('GATA1', 8, 28, '-', 'ACGATCGTAGCTAGCTAGGCTA', 3);


INSERT INTO `function` (code, name, category)
VALUES
('GO:0003700', 'Transcription factor activity', 'MF'),
('GO:0008150', 'DNA replication process', 'BP'),
('GO:0005737', 'Cytoplasmic localization', 'CC');


INSERT INTO gene_function (gene_id, function_id, evidence)
VALUES
(1, 1, 'experimental'),     
(1, 2, 'predicted'),        
(2, 2, 'experimental'),     
(3, 3, 'computational'),    
(5, 1, 'predicted');        



SELECT * FROM species;
SELECT * FROM genome;
SELECT * FROM chromosome;
SELECT * FROM gene;
SELECT * FROM `function`;
SELECT * FROM gene_function;

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