/*
 * DictTool is a command-line tool for performing operations on .dictx files, such
 * as automated conversion, merging and validation.
 */

using System;
using System.CommandLine;
using System.CommandLine.NamingConventionBinder;
using System.IO;
using System.Reflection.Metadata;
using DictionaryDocument;

namespace DictTool
{
    class Program
    {
        private const string DEFAULT_INPUT_FILE = "input.xml";
        private const string DEFAULT_OUTPUT_FILE = "output.xml";

        static int Main(string[] args)
        {
            var rootCommand = new RootCommand
            {
                Description = "DictX Command-Line Tool",

                Handler = CommandHandler.Create(() =>
                    {
                        Console.WriteLine($"DictX Command-Line Tool");
                        Console.WriteLine($"Run with the -h command line argument for help");
                    })
            };

            // Global options
            Option<FileInfo> inputFileOption = new(
                name: "--input",
                description: "Input file name.",
                getDefaultValue: () => new FileInfo(DEFAULT_INPUT_FILE)
            )
            {
                AllowMultipleArgumentsPerToken = true,
            };
            Option<FileInfo> outputFileOption = new(
                name: "--output",
                description: "Output file name.",
                getDefaultValue: () => new FileInfo(DEFAULT_OUTPUT_FILE)
            )
            {
                AllowMultipleArgumentsPerToken = true,
            };

            // Subcommands

#if DEBUG
            // Generate mock document and write it to the specified file.
            var testOutputCommand = new Command("test-output")
            {
                Description = "(Debug build only) Save a mock document to a specified file."
            };
            testOutputCommand.AddGlobalOption(outputFileOption);
            testOutputCommand.Handler = CommandHandler.Create<FileInfo>((output) =>
            {
                output ??= new FileInfo(DEFAULT_OUTPUT_FILE);
                TestOutput(output);
            });
            rootCommand.Add(testOutputCommand);

            // Generate mock document and write it to the specified file.
            var cloneDocumentCommand = new Command("clone-document")
            {
                Description = "(Debug build only) Load a document, then save the document into another XML file."
            };
            cloneDocumentCommand.AddGlobalOption(inputFileOption);
            cloneDocumentCommand.AddGlobalOption(outputFileOption);
            cloneDocumentCommand.Handler = CommandHandler.Create<FileInfo, FileInfo>((input, output) =>
            {
                input ??= new FileInfo(DEFAULT_INPUT_FILE);
                output ??= new FileInfo(DEFAULT_OUTPUT_FILE);
                CloneOutput(input, output);
            });
            rootCommand.Add(cloneDocumentCommand);

            // This is a playground for random experimentation.
            var playgroundCommand = new Command("playground")
            {
                Description = "(Debug build only) Run the experiment in a Playground() function.",
                Handler = CommandHandler.Create(() =>
                    {
                        Playground();
                    })
            };
            rootCommand.Add(playgroundCommand);
#endif

            // Validate DictX document structure.
            var validationCommand = new Command("validate")
            {
                Description = "Validate a DictX document structure."
            };
            validationCommand.Add(new Argument<FileInfo>(name: "input",
                    description: "Input file name.",
                    getDefaultValue: () => new FileInfo("dictionary.xml")));
            validationCommand.Handler = CommandHandler.Create<FileInfo>((input) =>
            {
                input ??= new FileInfo("dictionary.xml");
                ValidateDocument(input);
            });
            rootCommand.Add(validationCommand);

            // Parse the incoming args and invoke the handler
            return rootCommand.InvokeAsync(args).Result;
        }

#if DEBUG
        public static void TestOutput(FileInfo outputFile)
        {
            var d = DictionaryDocument.Generators.GetMockDocument();
            Console.WriteLine($"Writing a mock document to {outputFile.FullName}");
            d.SaveDictx(outputFile);
        }
        public static void CloneOutput(FileInfo inputFile, FileInfo outputFile)
        {
            var d = DictionaryDocument.Dictionary.LoadDictx(inputFile);
            Console.WriteLine($"Writing a clone of {inputFile.FullName} to {outputFile.FullName}");
            d.SaveDictx(outputFile);
        }
        public static void Playground()
        {
            // This function is for random tests 'n' stuff.

            var builtMockDocument = Generators.GetMockDocument();

            string generatedMockDocumentFile = @"DictionaryDocument.Tests/TestFiles/mock_document.xml";
            var mockFile = new FileInfo(generatedMockDocumentFile);
            Dictionary mockDocument1 = Dictionary.LoadDictx(mockFile);
            Dictionary mockDocument2 = Dictionary.LoadDictx(mockFile);
            Console.WriteLine($"Hash for document 1: {mockDocument1.GetHashCode()}");
            Console.WriteLine($"Hash for document 2: {mockDocument2.GetHashCode()}");

            if (mockDocument1 == mockDocument2)
                Console.WriteLine("Two instances of loading same document matches");
            else
                Console.WriteLine("NO MATCH!");

            var testFile = new FileInfo(@"output.xml");
            mockDocument1.SaveDictx(testFile);
            var loadedMockDocument1 = Dictionary.LoadDictx(testFile);
            if (mockDocument1 == loadedMockDocument1)
                Console.WriteLine($"Document roundtrip works");
            else
                Console.WriteLine($"ROUNDTRIP FAIL!");

            builtMockDocument.SaveDictx(testFile);
            Console.WriteLine($"Hash for API-built mock document: {builtMockDocument.GetHashCode()}");
            if (mockDocument1 == builtMockDocument)
                Console.WriteLine("API's mock document matches loaded mock document");
            else
                Console.WriteLine("API/LOADED MISMATCH");
        }
#endif

        public static void ValidateDocument(FileInfo inputFile)
        {
            bool result = DictionaryDocument.Dictionary.ValidateDictx(inputFile);
            switch (result)
            {
                case true:
                    Console.WriteLine($"{inputFile.FullName} is a valid DictX document.");
                    break;
                case false:
                    Console.WriteLine($"{inputFile.FullName} is an invalid DictX document!");
                    break;
            }
        }
    }
}
