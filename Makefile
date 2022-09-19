ifndef VERBOSE
	MAKEFLAGS += --no-print-directory
  .SILENT:
endif

.PHONY: main
run:
	javac -d build Main.java && java -cp build Main

.PHONY: build
build:
	javac -d build Main.java

LABS = $(wildcard lab*)
.PHONY: $(LABS)
$(LABS): %:
	${MAKE} -C $* run
