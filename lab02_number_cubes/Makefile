ifndef VERBOSE
  .SILENT:
endif

.PHONY: run build watch

run: build
	java -cp ../build lab02_number_cubes.LibreYachts

build:
	javac -cp .. -d ../build LibreYachts.java

watch:
	watchexec -e java -- ${MAKE} run
