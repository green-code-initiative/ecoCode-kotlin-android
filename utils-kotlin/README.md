# Kotlin Utils

The Kotlin Utils provide certain meta-level functionality for this project, such as printing the AST of a kts file.

## Printing ASTs to Dot

You can print the AST generated from a `*.kt` or `*.kts` file in TXT or DOT (Graphviz) format, either on `stdout` or to a file. To print the AST to
`stdout`:

1. Run `./gradlew utils-kotlin:printAst --args="<txt|dot> <path-to-input-kotlin-file>"`

To print the AST into an output file, simply add the path of the output file as second command line argument:

1. Run `./gradlew utils-kotlin:printAst --args="<txt|dot> <path-to-input-kotlin-file> <path-to-output-file>"`

So for instance, if you want to visualize the AST for the file `Example.kt` do the following (you'll need to install graphviz for this):

```
./gradlew utils-kotlin:printAst --args="dot Example.kt /tmp/ast.dot"
dot -Tpng /tmp/ast.dot -o /tmp/ast.png
```

Now open the file `/tmp/ast.png` with an image viewer of your choice. See the documentation of the `dot` tool to find various
alternative output formats.
