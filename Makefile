ifndef VERBOSE
	MAKEFLAGS += --no-print-directory
  .SILENT:
endif

LABS = $(wildcard lab*)

.PHONY: run build clean $(LABS)

run: build
	${MAKE} _run

_run:
	java -cp build Main

build:
	javac -d build Main.java

clean:
	rm -rf build

$(LABS): %:
	${MAKE} -C $* run
