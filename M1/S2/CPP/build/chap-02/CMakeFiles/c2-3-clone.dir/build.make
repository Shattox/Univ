# CMAKE generated file: DO NOT EDIT!
# Generated by "MinGW Makefiles" Generator, CMake Version 3.22

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:

#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:

# Disable VCS-based implicit rules.
% : %,v

# Disable VCS-based implicit rules.
% : RCS/%

# Disable VCS-based implicit rules.
% : RCS/%,v

# Disable VCS-based implicit rules.
% : SCCS/s.%

# Disable VCS-based implicit rules.
% : s.%

.SUFFIXES: .hpux_make_needs_suffix_list

# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

#Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:
.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

SHELL = cmd.exe

# The CMake executable.
CMAKE_COMMAND = "C:\Program Files\CMake\bin\cmake.exe"

# The command to remove a file.
RM = "C:\Program Files\CMake\bin\cmake.exe" -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = C:\Users\Steve\Documents\CPP\CPP_Learning

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = C:\Users\Steve\Documents\CPP\CPP_Learning\build

# Include any dependencies generated for this target.
include chap-02/CMakeFiles/c2-3-clone.dir/depend.make
# Include any dependencies generated by the compiler for this target.
include chap-02/CMakeFiles/c2-3-clone.dir/compiler_depend.make

# Include the progress variables for this target.
include chap-02/CMakeFiles/c2-3-clone.dir/progress.make

# Include the compile flags for this target's objects.
include chap-02/CMakeFiles/c2-3-clone.dir/flags.make

chap-02/CMakeFiles/c2-3-clone.dir/3-clone.cpp.obj: chap-02/CMakeFiles/c2-3-clone.dir/flags.make
chap-02/CMakeFiles/c2-3-clone.dir/3-clone.cpp.obj: ../chap-02/3-clone.cpp
chap-02/CMakeFiles/c2-3-clone.dir/3-clone.cpp.obj: chap-02/CMakeFiles/c2-3-clone.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=C:\Users\Steve\Documents\CPP\CPP_Learning\build\CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object chap-02/CMakeFiles/c2-3-clone.dir/3-clone.cpp.obj"
	cd /d C:\Users\Steve\Documents\CPP\CPP_Learning\build\chap-02 && C:\msys64\mingw64\bin\x86_64-w64-mingw32-g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT chap-02/CMakeFiles/c2-3-clone.dir/3-clone.cpp.obj -MF CMakeFiles\c2-3-clone.dir\3-clone.cpp.obj.d -o CMakeFiles\c2-3-clone.dir\3-clone.cpp.obj -c C:\Users\Steve\Documents\CPP\CPP_Learning\chap-02\3-clone.cpp

chap-02/CMakeFiles/c2-3-clone.dir/3-clone.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/c2-3-clone.dir/3-clone.cpp.i"
	cd /d C:\Users\Steve\Documents\CPP\CPP_Learning\build\chap-02 && C:\msys64\mingw64\bin\x86_64-w64-mingw32-g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E C:\Users\Steve\Documents\CPP\CPP_Learning\chap-02\3-clone.cpp > CMakeFiles\c2-3-clone.dir\3-clone.cpp.i

chap-02/CMakeFiles/c2-3-clone.dir/3-clone.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/c2-3-clone.dir/3-clone.cpp.s"
	cd /d C:\Users\Steve\Documents\CPP\CPP_Learning\build\chap-02 && C:\msys64\mingw64\bin\x86_64-w64-mingw32-g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S C:\Users\Steve\Documents\CPP\CPP_Learning\chap-02\3-clone.cpp -o CMakeFiles\c2-3-clone.dir\3-clone.cpp.s

# Object files for target c2-3-clone
c2__3__clone_OBJECTS = \
"CMakeFiles/c2-3-clone.dir/3-clone.cpp.obj"

# External object files for target c2-3-clone
c2__3__clone_EXTERNAL_OBJECTS =

chap-02/c2-3-clone.exe: chap-02/CMakeFiles/c2-3-clone.dir/3-clone.cpp.obj
chap-02/c2-3-clone.exe: chap-02/CMakeFiles/c2-3-clone.dir/build.make
chap-02/c2-3-clone.exe: chap-02/CMakeFiles/c2-3-clone.dir/linklibs.rsp
chap-02/c2-3-clone.exe: chap-02/CMakeFiles/c2-3-clone.dir/objects1.rsp
chap-02/c2-3-clone.exe: chap-02/CMakeFiles/c2-3-clone.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=C:\Users\Steve\Documents\CPP\CPP_Learning\build\CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable c2-3-clone.exe"
	cd /d C:\Users\Steve\Documents\CPP\CPP_Learning\build\chap-02 && $(CMAKE_COMMAND) -E cmake_link_script CMakeFiles\c2-3-clone.dir\link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
chap-02/CMakeFiles/c2-3-clone.dir/build: chap-02/c2-3-clone.exe
.PHONY : chap-02/CMakeFiles/c2-3-clone.dir/build

chap-02/CMakeFiles/c2-3-clone.dir/clean:
	cd /d C:\Users\Steve\Documents\CPP\CPP_Learning\build\chap-02 && $(CMAKE_COMMAND) -P CMakeFiles\c2-3-clone.dir\cmake_clean.cmake
.PHONY : chap-02/CMakeFiles/c2-3-clone.dir/clean

chap-02/CMakeFiles/c2-3-clone.dir/depend:
	$(CMAKE_COMMAND) -E cmake_depends "MinGW Makefiles" C:\Users\Steve\Documents\CPP\CPP_Learning C:\Users\Steve\Documents\CPP\CPP_Learning\chap-02 C:\Users\Steve\Documents\CPP\CPP_Learning\build C:\Users\Steve\Documents\CPP\CPP_Learning\build\chap-02 C:\Users\Steve\Documents\CPP\CPP_Learning\build\chap-02\CMakeFiles\c2-3-clone.dir\DependInfo.cmake --color=$(COLOR)
.PHONY : chap-02/CMakeFiles/c2-3-clone.dir/depend

