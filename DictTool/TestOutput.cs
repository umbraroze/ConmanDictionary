/*
 * This is the "application logic" of the DictTool utility. These get called from the main program.
 * These may or may not be useful for any purpose.
 */

using System;
using System.IO;

namespace DictTool
{
    class DictToolUtility
    {
        public static void TestOutput(FileInfo outputFile)
        {
            var d = DictionaryDocument.Tests.Generators.GetMockDocument();
            Console.WriteLine($"Writing a mock document to {outputFile.FullName}");
            d.SaveDictx(outputFile);
        }

        public static void ValidateDocument(FileInfo inputFile)
        {
            bool result = DictionaryDocument.Dictionary.ValidateDictx(inputFile);
            switch(result)
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
