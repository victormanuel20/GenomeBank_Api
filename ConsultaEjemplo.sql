USE genomebank;


SELECT 
    g.id, 
    g.symbol, 
    c.name AS chromosome, 
    ge.version AS genome
FROM gene g
JOIN chromosome c ON g.chromosome_id = c.id
JOIN genome ge ON c.genome_id = ge.id;


SELECT 
    g.symbol, 
    f.code, 
    f.name AS function_name, 
    gf.evidence
FROM gene g
JOIN gene_function gf ON gf.gene_id = g.id
JOIN `function` f ON f.id = gf.function_id
WHERE g.symbol = 'BRCA1';