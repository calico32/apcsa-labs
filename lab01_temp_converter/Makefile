ifndef VERBOSE
  .SILENT:
endif

.PHONY: run
run:
	javac -cp .. -d ../build TempConverter.java
	java -cp ../build lab01_temp_converter.TempConverter

.PHONY: watch
watch:
	watchexec -e java -- ${MAKE} run
