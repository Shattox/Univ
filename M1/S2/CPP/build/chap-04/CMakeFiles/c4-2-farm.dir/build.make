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
include chap-04/CMakeFiles/c4-2-farm.dir/depend.make
# Include any dependencies generated by the compiler for this target.
include chap-04/CMakeFiles/c4-2-farm.dir/compiler_depend.make

# Include the progress variables for this target.
include chap-04/CMakeFiles/c4-2-farm.dir/progress.make

# Include the compile flags for this target's objects.
include chap-04/CMakeFiles/c4-2-farm.dir/flags.make

chap-04/CMakeFiles/c4-2-farm.dir/2-farm/FarmHouse.cpp.obj: chap-04/CMakeFiles/c4-2-farm.dir/flags.make
chap-04/CMakeFiles/c4-2-farm.dir/2-farm/FarmHouse.cpp.obj: ../chap-04/2-farm/FarmHouse.cpp
chap-04/CMakeFiles/c4-2-farm.dir/2-farm/FarmHouse.cpp.obj: chap-04/CMakeFiles/c4-2-farm.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=C:\Users\Steve\Documents\CPP\CPP_Learning\build\CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object chap-04/CMakeFiles/c4-2-farm.dir/2-farm/FarmHouse.cpp.obj"
	cd /d C:\Users\Steve\Documents\CPP\CPP_Learning\build\chap-04 && C:\msys64\mingw64\bin\x86_64-w64-mingw32-g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT chap-04/CMakeFiles/c4-2-farm.dir/2-farm/FarmHouse.cpp.obj -MF CMakeFiles\c4-2-farm.dir\2-farm\FarmHouse.cpp.obj.d -o CMakeFiles\c4-2-farm.dir\2-farm\FarmHouse.cpp.obj -c C:\Users\Steve\Documents\CPP\CPP_Learning\chap-04\2-farm\FarmHouse.cpp

chap-04/CMakeFiles/c4-2-farm.dir/2-farm/FarmHouse.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/c4-2-farm.dir/2-farm/FarmHouse.cpp.i"
	cd /d C:\Users\Steve\Documents\CPP\CPP_Learning\build\chap-04 && C:\msys64\mingw64\bin\x86_64-w64-mingw32-g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E C:\Users\Steve\Documents\CPP\CPP_Learning\chap-04\2-farm\FarmHouse.cpp > CMakeFiles\c4-2-farm.dir\2-farm\FarmHouse.cpp.i

chap-04/CMakeFiles/c4-2-farm.dir/2-farm/FarmHouse.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/c4-2-farm.dir/2-farm/FarmHouse.cpp.s"
	cd /d C:\Users\Steve\Documents\CPP\CPP_Learning\build\chap-04 && C:\msys64\mingw64\bin\x86_64-w64-mingw32-g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S C:\Users\Steve\Documents\CPP\CPP_Learning\chap-04\2-farm\FarmHouse.cpp -o CMakeFiles\c4-2-farm.dir\2-farm\FarmHouse.cpp.s

# Object files for target c4-2-farm
c4__2__farm_OBJECTS = \
"CMakeFiles/c4-2-farm.dir/2-farm/FarmHouse.cpp.obj"

# External object files for target c4-2-farm
c4__2__farm_EXTERNAL_OBJECTS =

chap-04/c4-2-farm.exe: chap-04/CMakeFiles/c4-2-farm.dir/2-farm/FarmHouse.cpp.obj
chap-04/c4-2-farm.exe: chap-04/CMakeFiles/c4-2-farm.dir/build.make
chap-04/c4-2-farm.exe: chap-04/CMakeFiles/c4-2-farm.dir/linklibs.rsp
chap-04/c4-2-farm.exe: chap-04/CMakeFiles/c4-2-farm.dir/objects1.rsp
chap-04/c4-2-farm.exe: chap-04/CMakeFiles/c4-2-farm.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=C:\Users\Steve\Documents\CPP\CPP_Learning\build\CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable c4-2-farm.exe"
	cd /d C:\Users\Steve\Documents\CPP\CPP_Learning\build\chap-04 && $(CMAKE_COMMAND) -E cmake_link_script CMakeFiles\c4-2-farm.dir\link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
chap-04/CMakeFiles/c4-2-farm.dir/build: chap-04/c4-2-farm.exe
.PHONY : chap-04/CMakeFiles/c4-2-farm.dir/build

chap-04/CMakeFiles/c4-2-farm.dir/clean:
	cd /d C:\Users\Steve\Documents\CPP\CPP_Learning\build\chap-04 && $(CMAKE_COMMAND) -P CMakeFiles\c4-2-farm.dir\cmake_clean.cmake
.PHONY : chap-04/CMakeFiles/c4-2-farm.dir/clean

chap-04/CMakeFiles/c4-2-farm.dir/depend:
	$(CMAKE_COMMAND) -E cmake_depends "MinGW Makefiles" C:\Users\Steve\Documents\CPP\CPP_Learning C:\Users\Steve\Documents\CPP\CPP_Learning\chap-04 C:\Users\Steve\Documents\CPP\CPP_Learning\build C:\Users\Steve\Documents\CPP\CPP_Learning\build\chap-04 C:\Users\Steve\Documents\CPP\CPP_Learning\build\chap-04\CMakeFiles\c4-2-farm.dir\DependInfo.cmake --color=$(COLOR)
.PHONY : chap-04/CMakeFiles/c4-2-farm.dir/depend

