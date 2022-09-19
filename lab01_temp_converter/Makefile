ifndef VERBOSE
  .SILENT:
endif

.PHONY: run build watch

run: build
	java -cp ../build lab01_temp_converter.TempConverter

build:
	javac -cp .. -d ../build TempConverter.java

watch:
	watchexec -e java -- ${MAKE} run
