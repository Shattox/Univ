/*!==================================================================
  E.Incerti - Universite Gustave Eiffel - eric.incerti@univ-eiffel.fr
       - Librairie G3X - Fonctions de base d'acces public -
                    version 6d - Jan.2022
  ==================================================================!*/
#ifdef __cplusplus
  extern "C" {
#else
  #define _GNU_SOURCE
#endif

#ifndef _G3X_TOOLS_H_
  #define _G3X_TOOLS_H_

  #include <g3x_types.h>

  /* horloge */
  typedef struct
  {
    uint hh; /* heure [0,23]     */
    uint mm; /* minutes [0,59]   */
    uint ss; /* secondes [0,59]  */
    uint ds; /* dizieme  de sec. */
    uint cs; /* centieme de sec. */
    uint ms; /* millieme de sec. */
  } G3Xclock;

  /* calcule le temps "processeur" (cumul des threads) */
  /* retour au format hh:mm:ss:cs:ms                   */
  char* g3x_ProcTimer(bool reset);
  /* chrono "temps r�el"                               */
  char*  g3x_Chrono(bool reset);
  /* r�cup�re le chrono courant                        */
  void   g3x_GetTime(G3Xclock* clock);
  /* temporisation, en seconde                         */
  int    g3x_tempo(double tempo);

  /*!****************************************************************************!*/
  /*! g�n�rateurs al�atoires                                                     !*/
  /*! DANS CETTE VERSION, C'EST LA GRAINE PAR DEFAUT                             !*/
  /*! => � l'utiilsateur de faire appel � rand(seed)                             !*/
  /*!****************************************************************************!*/
  /* intervalle [root*(1.-percent), root*(1.+percent)] */
  double g3x_Rand_Percent(double root, double percent);
  /* intervalle [root-delta, root+delta]               */
  double g3x_Rand_Delta(double root, double delta);
  /* intervalle [min, max]                             */
  double g3x_Rand_MinMax(double min, double max);


 #endif

#ifdef __cplusplus
  }
#endif
/*!=============================================================!*/
