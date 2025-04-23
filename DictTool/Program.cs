/*
 * DictTool is a command-line tool for performing operations on .dictx files, such
 * as automated conversion, merging and validation.
 */

using System;
using System.CommandLine;
using System.CommandLine.NamingConventionBinder;
using System.IO;

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
                // Root options should go here.
            };
            rootCommand.Description = "DictX Command-Line Tool";

            rootCommand.Handler = CommandHandler.Create(() =>
            {
                // Root handler should go here. Do nothing for now. Should probably bail out with usage instead.
            });

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
            var testOutputCommand = new Command("test-output");
            testOutputCommand.Description = "(Debug build only) Save a mock document to a specified file.";
            testOutputCommand.AddGlobalOption(outputFileOption);
            testOutputCommand.Handler = CommandHandler.Create<FileInfo>((output) =>
            {
                output ??= new FileInfo(DEFAULT_OUTPUT_FILE);
                TestOutput(output);
            });
            rootCommand.Add(testOutputCommand);

            // Generate mock document and write it to the specified file.
            var cloneDocumentCommand = new Command("clone-document");
            cloneDocumentCommand.Description = "(Debug build only) Load a document, then save the document into another XML file.";
            cloneDocumentCommand.AddGlobalOption(inputFileOption);
            cloneDocumentCommand.AddGlobalOption(outputFileOption);
            cloneDocumentCommand.Handler = CommandHandler.Create<FileInfo,FileInfo>((input,output) =>
            {
                input ??= new FileInfo(DEFAULT_INPUT_FILE);
                output ??= new FileInfo(DEFAULT_OUTPUT_FILE);
                CloneOutput(input,output);
            });
            rootCommand.Add(cloneDocumentCommand);
#endif

            // Validate DictX document structure.
            var validationCommand = new Command("validate");
            validationCommand.Description = "Validate a DictX document structure.";
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
