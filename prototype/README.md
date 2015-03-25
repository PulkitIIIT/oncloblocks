Please checkout branch jersey from this repo to see changes that I have made.

RUN: git checkout jersey

# Changes made by Pulkit to the protoype

1) Use Jersey to implement Mutation and Gene APi
2) Design an interface to interact with Database tables( collections in case of MongoDb). This can help in faster 
   testing and benchmarking with various databases


# Prototype code for OncoBlocks Data Block

## About

This directory contains prototype code for the OncoBlocks Data Block.  It is intended as a sandbox to try out new ideas.

## Current Status of Prototype

The prototype is currently very simple.  It stores mutation data to MongoDB and then makes the data available a few simple JSON web services.

## Prerequisites

* http://www.mongodb.com/:  you must download, and install mongoDB.  Once installed, run as daemon:  

		mongod
		
## Running Unit Tests

Run:
	mvn test
	
## Loading up Sample Data

The data folder contains a trivial set of data files:

* TCGA_codes.txt:  currently only contains TCGA Ovarian cancer.
* human_genes.txt:  currently only contains info for one gene:  TTN.
* ov_tcga.maf.txt:  currently only contains somatic mutations for TTN.

To load this data, run:

	./init.sh

## Accessing the Web Services

Once you have loaded the sample data, run:

	mvn tomcat:run
		
Sample queries: (These wont work with the new changes)

* [Get Gene Info for TTN](http://localhost:8080/oncoblocks/webservice.do?query=get_gene&gene_symbol=TTN)
* [Get list of Cancer Studies within the Block](http://localhost:8080/oncoblocks/webservice.do?query=get_cancer_studies)
* [Get all mutations involving TTN](http://localhost:8080/oncoblocks/webservice.do?query=get_mutations&entrez_id=7273)
* [Get all mutations associated with case ID:  TCGA-25-1322](http://localhost:8080/oncoblocks/webservice.do?query=get_mutations&case_id=TCGA-25-1322)


Now with the new changes that I have made we can access the same queries as above by querying
* [Get Gene Info for TTN](http://localhost:8080/oncoblocks/gene?gene_symbol=TTN)
* [Get all mutations involving TTN](http://localhost:8080/oncoblocks/mutations?entrez_id=7273)
* [Get all mutations associated with case ID:  TCGA-25-1322](http://localhost:8080/oncoblocks/mutations?case_id=TCGA-25-1322)
