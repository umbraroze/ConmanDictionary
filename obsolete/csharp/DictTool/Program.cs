/*
 * DictTool is a command-line tool for performing operations on .dictx files, such
 * as automated conversion, merging and validation.
 */

using System;
using System.CommandLine;
using System.Diagnostics;
using System.IO;
using DictionaryDocument;

namespace DictTool
{
    class Program
    {
        private const string DEFAULT_INPUT_FILE = "input.xml";
        private const string DEFAULT_OUTPUT_FILE = "output.xml";

        static int Main(string[] args)
        {
#if DEBUG
            // Make debug messages go on the console
            Trace.Listeners.Add(new TextWriterTraceListener(Console.Out));
#endif

            RootCommand rootCommand = new("DictX Command-Line Tool");

            // Global options
            Option<FileInfo> inputFileOption = new("--input")
            {
                Description = "Input file name.",
                DefaultValueFactory = parseResult => new FileInfo(DEFAULT_INPUT_FILE),
                AllowMultipleArgumentsPerToken = true
            };
            Option<FileInfo> outputFileOption = new("--output")
            {
                Description = "Output file name.",
                DefaultValueFactory = parseResult => new FileInfo(DEFAULT_OUTPUT_FILE),
                AllowMultipleArgumentsPerToken = true
            };

            // Subcommands

#if DEBUG
            // Generate mock document and write it to the specified file.
            var testOutputCommand = new Command("test-output",
                "(Debug build only) Save a mock document to a specified file.")
            {
                outputFileOption
            };
            testOutputCommand.SetAction(parseResult =>
            {
                FileInfo output = parseResult.GetValue(outputFileOption);
                TestOutput(output);
                return 0;
            });
            rootCommand.Add(testOutputCommand);

            // Generate mock document and write it to the specified file.
            var cloneDocumentCommand = new Command("clone-document",
                "(Debug build only) Load a document, then save the document into another XML file.")
            {
                inputFileOption,
                outputFileOption
            };
            cloneDocumentCommand.SetAction(parseResult =>
            {
                FileInfo input = parseResult.GetValue(inputFileOption);
                FileInfo output = parseResult.GetValue(outputFileOption);
                CloneOutput(input, output);
                return 0;
            });
            rootCommand.Add(cloneDocumentCommand);

            // This is a playground for random experimentation.
            var playgroundCommand = new Command("playground","(Debug build only) Run the experiment in a Playground() function.");
            playgroundCommand.SetAction(parseResult =>
            {
                Playground();
                return 0;
            });
            rootCommand.Add(playgroundCommand);
#endif // DEBUG

            var validationInputFileArgument = new Argument<FileInfo>("input")
            {
                Description = "Input file name.",
                DefaultValueFactory = parseResult => new FileInfo("dictionary.xml"),
            };
            // Validate DictX document structure.
            Command validationCommand = new("validate", "Validate a DictX document structure.")
            {
                validationInputFileArgument
            };
            validationCommand.SetAction(parseResult =>
            {
                FileInfo input = parseResult.GetValue(validationInputFileArgument);
                ValidateDocument(input);
                return 0;
            });
            rootCommand.Add(validationCommand);

            rootCommand.SetAction(parseResult =>
            {
                Console.WriteLine($"DictX Command-Line Tool");
                Console.WriteLine($"Run with the -h command line argument for help");
                return 0;
            });

            // Command line parsing.
            var parseResult = rootCommand.Parse(args);
            if (parseResult.Errors.Count > 0)
            {
                foreach (var e in parseResult.Errors)
                {
                    Console.Error.WriteLine(e.Message);
                }
                return 1;
            }
            return parseResult.Invoke();
        }

        [Conditional("DEBUG")]
        public static void TestOutput(FileInfo outputFile)
        {
            var d = DictionaryDocument.Generators.GetMockDocument();
            Console.WriteLine($"Writing a mock document to {outputFile.FullName}");
            d.SaveDictx(outputFile);
        }

        [Conditional("DEBUG")]
        public static void CloneOutput(FileInfo inputFile, FileInfo outputFile)
        {
            var d = DictionaryDocument.Dictionary.LoadDictx(inputFile);
            Console.WriteLine($"Writing a clone of {inputFile.FullName} to {outputFile.FullName}");
            d.SaveDictx(outputFile);
        }

        [Conditional("DEBUG")]
        public static void Playground()
        {
            // This function is for random tests 'n' stuff.

            var builtMockDocument = Generators.GetMockDocument();

            var testFile1 = new FileInfo(@"output.xml");
            var testFile1d = new FileInfo(@"output_dump.txt");
            var testFile2 = new FileInfo(@"output2.xml");
            var testFile2d = new FileInfo(@"output2_dump.txt");
            var mockFile = new FileInfo(@"DictionaryDocument.Tests/TestFiles/mock_document.xml");

            Console.WriteLine("\n*** Same document loaded twice ***");
            Dictionary mockDocument1 = Dictionary.LoadDictx(mockFile);
            Dictionary mockDocument2 = Dictionary.LoadDictx(mockFile);
            Console.WriteLine($"Hash for document 1: {mockDocument1.GetHashCode()}");
            Console.WriteLine($"Hash for document 2: {mockDocument2.GetHashCode()}");
            if (mockDocument1 == mockDocument2)
                Console.WriteLine("Two instances of loading same document matches");
            else
                Console.WriteLine("NO MATCH!");

            Console.WriteLine("\n*** Roundtrip a document originally loaded from disk ***");
            mockDocument1.SaveDictx(testFile1);
            var loadedMockDocument1 = Dictionary.LoadDictx(testFile1);
            if (mockDocument1 == loadedMockDocument1)
                Console.WriteLine($"Document roundtrip works");
            else
                Console.WriteLine($"ROUNDTRIP FAIL!");

            Console.WriteLine("\n*** Compare to a generated document ***");
            Console.WriteLine($"Hash for API-built mock document: {builtMockDocument.GetHashCode()}");
            if (mockDocument1 == null)
            {
                Console.WriteLine("mockDocument1 is null");
            }
            if (builtMockDocument == null)
            {
                Console.WriteLine("builtMockDocument is null");
            }
            if (mockDocument1 == builtMockDocument)
                Console.WriteLine("API's mock document matches loaded mock document");
            else
                Console.WriteLine($"API/LOADED MISMATCH ({mockDocument1.GetHashCode()} / {builtMockDocument.GetHashCode()})");

            Console.WriteLine("\n*** Roundtrip a generated document ***");
            builtMockDocument.SaveDictx(testFile1);
            var loadedMockDocument3 = Dictionary.LoadDictx(testFile1);
            loadedMockDocument3.SaveDictx(testFile2);
            Console.WriteLine("Document 1:");
            builtMockDocument.DebugDump();
            Console.WriteLine("Document 2:");
            loadedMockDocument3.DebugDump();
            if (builtMockDocument == loadedMockDocument3)
                Console.WriteLine($"Generated mock document loading works");
            else
                Console.WriteLine($"GEN/LOAD MISMATCH ({builtMockDocument.GetHashCode()} / {loadedMockDocument3.GetHashCode()})");
            if (loadedMockDocument1 == loadedMockDocument3)
                Console.WriteLine($"Saved Generated mock document matches first loaded one");
            else
                Console.WriteLine($"GEN/FIRST MISMATCH ({loadedMockDocument1.GetHashCode()} / {loadedMockDocument3.GetHashCode()})");
        }

        public static void ValidateDocument(FileInfo inputFile)
        {
            try {
                bool result = DictionaryDocument.Dictionary.ValidateDictx(inputFile);
                switch (result)
                {
                    case true:
                        Console.WriteLine($"{inputFile.FullName} is a valid DictX document.");
                        Environment.Exit(0);
                        break;
                    case false:
                        Console.Error.WriteLine($"{inputFile.FullName} is an invalid DictX document!");
                        Environment.Exit(1);
                        break;
                }
            }
            catch (Exception e)
            {
                Console.Error.WriteLine($"Error: {e.Message}");
                Environment.Exit(1);
            }
        }
    }
}
