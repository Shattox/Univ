/*!==================================================================
  E.Incerti - Universite Gustave Eiffel - eric.incerti@univ-eiffel.fr
       - Librairie G2X - Fonctions de base d'acces public -
                    version 6d - Jan.2022
  ==================================================================!*/
#ifdef __cplusplus
  extern "C" {
#else
  #define _GNU_SOURCE
#endif

#ifndef _G2X_TYPES_H
  #define _G2X_TYPES_H

  /*!           DEFINITION DE QUELQUES TYPES           !*/
  /* definition et macros pour le type <unsigned char>  */
  #ifndef _UCHAR_
    #define _UCHAR_
    #include <limits.h>
    typedef unsigned char uchar;
    #define UCHARSIZE CHAR_BIT*sizeof(uchar)
  #endif
  /* definition et macros pour le type <unsigned short> */
  #ifndef _USHORT_
    #define _USHORT_
    #include <limits.h>
    typedef unsigned short ushort;
    #define USHORTSIZE CHAR_BIT*sizeof(ushort)
  #endif
  /* definition et macros pour le type <unsigned int> */
  #ifndef _UINT_
    #define _UINT_
    #include <limits.h>
    typedef unsigned int uint;
    #define UINTSIZE CHAR_BIT*sizeof(uint)
  #endif

  /* definition et macros pour le type <long int> */
  #ifndef _LINT_
    #define _LINT_
    #include <limits.h>
    typedef long int lint;
    #define LINTSIZE CHAR_BIT*sizeof(lint)
  #endif
  #ifndef _ULINT_
    #define _ULINT_
    #include <limits.h>
    typedef unsigned long int ulint;
    #define ULINTSIZE CHAR_BIT*sizeof(ulint)
  #endif
  /* definition et macros pour le type <bool> */
  #ifndef _BOOL_
    #define _BOOL_
    #ifndef __cplusplus
      typedef enum {FALSE=0x0,false=0x0,TRUE=0x1,true=0x1} bool;
    #endif
  #endif

  /* Quelques constantes numeriques toujours utiles.. */
  #define PI        3.1415926535897932384626433832795
  /* PI/180� : conversion degr� => radian             */
  #define DegToRad  0.0174532925199433
  /* 180�/PI : conversion radian => degr�             */
  #define RadToDeg 57.29577951308232
  /* sqrt(2.)                                         */
  #define Racin2    1.414213562373095

  #define SQR(a)          ((a)*(a))
  #define MIN(a,b)        (((a)<(b))?(a):(b))
  #define MAX(a,b)        (((a)<(b))?(b):(a))
  #define MIN3(a,b,c)     (((a)<(b))?(((a)<(c))?(a):(c)):(((b)<(c))?(b):(c)))
  #define MAX3(a,b,c)     (((a)>(b))?(((a)>(c))?(a):(c)):(((b)>(c))?(b):(c)))
  #define CLIP(min,a,max) (((a)<(min)?(min):((a)>(max)?(max):(a))))

  /* pour remplacer les tests d'egalite sur les reels */
  #define ZERO 1.e-20
  #define G2Xiszero(x) (((x)<ZERO && (x)>-ZERO)?true:false)

#endif

#ifdef __cplusplus
  }
#endif
/*!===========================================================================!*/
