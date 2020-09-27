/*
 * DictTool is a command-line tool for performing operations on .dictx files, such
 * as automated conversion, merging and validation.
 */

using System;
using System.CommandLine;
using System.CommandLine.Invocation;
using System.IO;
using DictionaryDocument;

namespace DictTool
{
    class Program
    {
        static int Main(string[] args)
        {
            // Example code is from https://github.com/dotnet/command-line-api/blob/main/docs/Your-first-app-with-System-CommandLine.md

            // Create a root command with some options
            var rootCommand = new RootCommand
            {
                new Option<int>(
                    "--int-option",
                    getDefaultValue: () => 42,
                    description: "An option whose argument is parsed as an int"),
                new Option<bool>(
                    "--bool-option",
                    "An option whose argument is parsed as a bool"),
                new Option<FileInfo>(
                    "--file-option",
                    "An option whose argument is parsed as a FileInfo")
            };
            rootCommand.Description = "DictX Command-Line Tool";

            // Note that the parameters of the handler method are matched according to the names of the options
            rootCommand.Handler = CommandHandler.Create<int, bool, FileInfo>((intOption, boolOption, fileOption) =>
            {
                Console.WriteLine($"The value for --int-option is: {intOption}");
                Console.WriteLine($"The value for --bool-option is: {boolOption}");
                Console.WriteLine($"The value for --file-option is: {fileOption?.FullName ?? "null"}");
            });

            // Subcommands
            var testOutputCommand = new Command("test-output");
            testOutputCommand.Description = "A rudimentary test of outputting stuff.";
            testOutputCommand.Add(new Option<FileInfo>("--output", "Output file name."));
            testOutputCommand.Handler = CommandHandler.Create<FileInfo>((output) =>
            {
                output = output ?? new FileInfo("test.xml");
                DictToolUtility.TestOutput(output);
            });
            rootCommand.Add(testOutputCommand);

            // Parse the incoming args and invoke the handler
            return rootCommand.InvokeAsync(args).Result;
        }
    }
}
