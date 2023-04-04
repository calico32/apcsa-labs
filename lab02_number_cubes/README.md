# LibreYachts

A FOSS TUI clone of Yahtzee™.

## Overview

**`LibreYachts`**: main
**`Game`**: game loop/logic, ui, and input
**`Scoresheet`**: scoring data container and drawing
  - **`Table`**: generic-ish table drawing helpers
**`Hand`**: dice data container and drawing
**`NumberCube`**: politically correct dice class responsible for rolling and drawing dice
**`.roll_category.RollCategory`**: abstract class for scoring categories
  - **`.RollCategoryOfAKind`**: "X of a kind" scoring categories
  - **`.RollCategoryStraight`**: small/large straight scoring categories
  - **`.RollCategoryUpper`**: upper section scoring categories

## Running

[![Run on Repl.it](https://repl.it/badge/github/calico32/apcsa-labs)](https://repl.it/@calico32/apcsa-labs)

Generate a new `Makefile` from the repo root (only needed once):

```bash
./configure
```

Run from this directory:
```bash
make
```

Run from repo root:

```bash
make lab02_number_cubes
```
