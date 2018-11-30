#pragma once
/****************************************************/
/* File: scan.h                                     */
/* The scanner interface for the TINY compiler      */
/* Compiler Construction: Principles and Practice   */
/* Kenneth C. Louden                                */
/****************************************************/

#ifndef _SCAN_H_
#define _SCAN_H_


/* MAXTOKENLEN is the maximum size of a token */
#define MAXTOKENLEN 40

/* tokenString array stores the lexeme of each token */
extern char tokenString[MAXTOKENLEN + 1];

/* function getToken returns the
 * next token in source file
 */
TokenTypeInProjece getToken(void);

extern  int linepos; /* current position in LineBuf */
extern  int bufsize; /* current size of buffer string */
extern  int EOF_flag;

#endif