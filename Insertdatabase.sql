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
