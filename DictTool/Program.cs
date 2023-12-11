/*
 * DictTool is a command-line tool for performing operations on .dictx files, such
 * as automated conversion, merging and validation.
 */

using System.CommandLine;
using System.CommandLine.NamingConventionBinder;
using System.IO;

namespace DictTool
{
    class Program
    {
        static int Main(string[] args)
        {
            var rootCommand = new RootCommand
            {
                // Root options should go here.
            };
            rootCommand.Description = "DictX Command-Line Tool";

            rootCommand.Handler = CommandHandler.Create<int, bool, FileInfo>((intOption, boolOption, fileOption) =>
            {
                // Root handler should go here. Do nothing for now. Should probably bail out with usage instead.
            });

            // Subcommands

#if DEBUG
            // Generate mock document and write it to the specified file.
            var testOutputCommand = new Command("test-output");
            testOutputCommand.Description = "(Debug build only) Save a mock document to a specified file.";
            testOutputCommand.Add(new Argument<FileInfo>(name: "output",
                description: "Output file name.",
                getDefaultValue: () => new FileInfo("test.xml")));
            testOutputCommand.Handler = CommandHandler.Create<FileInfo>((output) =>
            {
                output = output ?? new FileInfo("test.xml");
                DictToolUtility.TestOutput(output);
            });
            rootCommand.Add(testOutputCommand);
#endif

            // Validate DictX document structure.
            var validationCommand = new Command("validate");
            validationCommand.Description = "Validate a DictX document structure.";
            validationCommand.Add(new Argument<FileInfo>(name: "input",
                    description: "Input file name.",
                    getDefaultValue: () => new FileInfo("dictionary.xml")));
            validationCommand.Handler = CommandHandler.Create<FileInfo>((input) =>
            {
                input = input ?? new FileInfo("dictionary.xml");
                DictToolUtility.ValidateDocument(input);
            });
            rootCommand.Add(validationCommand);

            // Parse the incoming args and invoke the handler
            return rootCommand.InvokeAsync(args).Result;
        }
    }
}
