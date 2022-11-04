ifndef VERBOSE
	MAKEFLAGS += --no-print-directory
  .SILENT:
endif

LABS = $(wildcard lab*)
SCRIPTS = $(wildcard scripts/*.java)

.PHONY: run build clean $(LABS) $(SCRIPTS)

define java
	for lab in $(LABS); do \
		CLASSPATH=$$CLASSPATH:$$lab/res; \
	done; \
	java -cp build$$CLASSPATH
endef

run: build
	${MAKE} _run

_run:
	$(java) Main

build:
	javac -d build Main.java

clean:
	rm -rf build

$(LABS): %:
	${MAKE} -C $* run

run-test: build-test
	$(java) Test

build-test:
	javac -d build Test.java

$(SCRIPTS): %:
	javac -d build $@
	java -cp build scripts.$(basename $(notdir $@))
