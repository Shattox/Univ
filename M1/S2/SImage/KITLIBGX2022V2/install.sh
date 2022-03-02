#!/bin/bash
printf '#---------------------------------------------------------------------------------\n# ' cat >> $HOME/.bashrc
date | cat >> $HOME/.bashrc
echo "# Environnement variables for the graphic libraries <g2x> <g3x>
#---------------------------------------------------------------------------------
# tests if the config. is 32 or 64 bits
case \`uname -m\` in
    i686) export PROCBIT=32b;;
    x86_64 | ia64) export PROCBIT=64b;;
esac
# g2x librairies
export G2X_PATH=$PWD/libg2x.6d
# g3x librairies
export G3X_PATH=$PWD/libg3x.6d
export lGL_PATH=/usr/lib/x86_64-linux-gnu
export iGL_PATH=/usr/include
# environnement varaibles for Makefile, with the good version (32b/64b) of <libg2x>
export libG2X='-lm -L\$(lGL_PATH) -lGL -lGLU -lglut -L\$(G2X_PATH) -lg2x.6d.\$(PROCBIT)'
export incG2X='-I\$(G2X_PATH)/include -I\$(iGL_PATH)'
export libG3X='-lm -L\$(lGL_PATH) -lGL -lGLU -lglut -L\$(G3X_PATH) -lg3x.6d.\$(PROCBIT)'
export incG3X='-I\$(G3X_PATH)/include -I\$(iGL_PATH)'
# export path for 'ld'
export LD_LIBRARY_PATH=\$G2X_PATH:\$G3X_PATH:\$LD_LIBRARY_PATH
#---------------------------------------------------------------------------------"| cat >> $HOME/.bashrc;
bash;

