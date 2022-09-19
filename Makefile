ifndef VERBOSE
	MAKEFLAGS += --no-print-directory
  .SILENT:
endif

LABS = $(wildcard lab*)

.PHONY: run build $(LABS)

run: build
	${MAKE} _run

_run:
	java -cp build Main

build:
	javac -d build Main.java

$(LABS): %:
	${MAKE} -C $* run
