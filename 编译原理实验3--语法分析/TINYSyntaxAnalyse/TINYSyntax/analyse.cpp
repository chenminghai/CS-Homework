/****************************************************/
/* File: main.c                                     */
/* Main program for TINY compiler                   */
/* Compiler Construction: Principles and Practice   */
/* Kenneth C. Louden                                */
/****************************************************/

#include "globals.h"
#include "stdafx.h"
#include "util.h"
#include "parse.h"
#include "scan.h"

/* allocate global variables */
int lineno = 0;
FILE * source;
FILE * listing;
FILE * code;

/* allocate and set tracing flags */
int EchoSource = FALSE;
int TraceScan = FALSE;
int TraceParse = TRUE;
int TraceAnalyze = FALSE;
int TraceCode = FALSE;

int Error = FALSE;

int analyse(string argv, string str)
{
	TreeNode * syntaxTree;
	char pgm[120]; /* source code file name */
	strcpy(pgm, argv.c_str());
	if (strchr(pgm, '.') == NULL)
		strcat(pgm, ".tny");
	source = fopen(pgm, "r");
	if (source == NULL)
	{
		fprintf(stderr, "File %s not found\n", pgm);
		exit(1);
	}
	listing = fopen(str.c_str(), "w");
	fprintf(listing, "\nTINY Syntax Analyse!\n");

	int linepos = 0;
	int bufsize = 0;
	EOF_flag = FALSE;
	syntaxTree = parse();

	fprintf(listing, "\nTINY Syntax tree:\n");
	printTree(syntaxTree);
	fclose(listing);
	fclose(source);
	return 0;
}