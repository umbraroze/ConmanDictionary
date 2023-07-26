﻿/*
 * 
 * This application exists mostly to try out various concepts during the development
 * without the overhead of the main application. In other words, I don't know a thing
 * about C#, I'm just fooling around here, this is not the real app, please go away.
 *
 */

using System;
using System.IO;

namespace DictTool
{
    class DictToolUtility
    {
        public static void TestOutput(FileInfo outputFile)
        {
            Console.WriteLine("Start of the app");

            Console.WriteLine("Fiddling.");
            var d = DictionaryDocument.Tests.Generators.GetMockDocument();

            Console.WriteLine($"Dumping to {outputFile.FullName}");
            d.SaveDictx(outputFile);

            Console.WriteLine("End of the app");
        }
    }
}
