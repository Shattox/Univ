/*=================================================================*/
/*= E.Incerti - eric.incerti@univ-eiffel.fr                       =*/
/*= Université Gustave Eiffel                                     =*/
/*= Code "squelette" pour prototypage avec libg3x.6c              =*/
/*=================================================================*/

#include <g3x.h>

#define NBM 720
#define NBP 720

/* tailles de la fenêtre (en pixel) */
static int WWIDTH=512, WHEIGHT=512;
static G3Xpoint P[NBP][NBM];

static void sphere(void) {
    double phi_p = PI / NBP;
    double theta_m = (2 * PI) / NBM;
    
    for (int p = 0; p < NBP; p++) {
        double zp = cos(p * phi_p);
        double rp = sin(p * phi_p);

        for (int m = 0; m < NBM; m++) {
            P[p][m].x = rp * cos(m * theta_m);
            P[p][m].y = rp * sin(m * theta_m);
            P[p][m].z = zp;
        }
    }
}

/* la fonction d'initialisation : appelée 1 seule fois, au début */
static void init(void) {
    /* glBegin(GL_QUADS);
        for (int p = 0; p < NBP - step; p += step) {
            for (int m = 0; m < NBM - step; m += step) {
                g3x_Vertex3dv(P[p][m]);
                g3x_Vertex3dv(P[p][m + step]);
                g3x_Vertex3dv(P[p + step][m + step]);
                g3x_Vertex3dv(P[p + step][m]);
                // fermer le bandeau
            }
        }
    glEnd(); */
    sphere();
}

/* la fonction de contrôle : appelée 1 seule fois, juste après <init> */
static void ctrl(void)
{
}

/* la fonction de dessin : appelée en boucle */
static void draw(void)
{
    int step = 15;
    glPointSize(4);
    glBegin(GL_LINES);
        for (int p = 0; p < NBP - step; p += step) {
            for (int m = 0; m < NBM - 1; m += step) {
               g3x_Normal3dv(P[p][m]);
               g3x_Vertex3dv(P[p][m + step]);
            }
            g3x_Vertex3dv(P[p][NBM - 1]);
            g3x_Vertex3dv(P[p][0]);
        }
        for (int m = 0; m <= NBM - step; m += step) //remplacer 1 par le ppas
    {
        for (int p = 0; p < NBP - 1; p += 1) //remplacer 1 par le mpas
        {
            //ici aussi
            g3x_Vertex3dv(P[p][m]);
            g3x_Vertex3dv(P[p + 1][m]);
        }
    }

    glEnd();
}

/* la fonction d'animation (facultatif) */
static void anim(void)
{
}

/* la fonction de sortie  (facultatif) -- atexit() */
static void quit(void)
{
}

/***************************************************************************/
/* La fonction principale : NE CHANGE JAMAIS ou presque                    */
/***************************************************************************/
int main(int argc, char **argv)
{
  /* creation de la fenetre - titre et tailles (pixels) */
  g3x_InitWindow(*argv,WWIDTH,WHEIGHT);

  g3x_SetInitFunction(init); /* fonction d'initialisation */
  g3x_SetCtrlFunction(ctrl); /* fonction de contrôle      */
  g3x_SetDrawFunction(draw); /* fonction de dessin        */
  g3x_SetAnimFunction(anim); /* fonction d'animation      */
  g3x_SetExitFunction(quit); /* fonction de sortie        */

  /* lancement de la boucle principale */
  return g3x_MainStart();
  /* RIEN APRES CA */
}
