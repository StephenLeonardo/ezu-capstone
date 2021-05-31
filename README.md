# ezu-capstone

You will need to create a separate folder like this:
-Dataset
 -clean_voice
 -noise
 -sound
 -spectrogram
 -time_series
 
 the clean voice dataset can be downloaded from http://www.openslr.org/12/ and the noise dataset from https://github.com/karolpiczak/ESC-50
 
 Add the clean voice dataset into the clean_voice dirs, and noise dataset into the noise dir
 
 Then in the model folder, change the parameters in args.py according to the filepath of the dirs specified above.
 
 run in console python main.py --mode=data_creation
 
 after that you can run the Training.ipynb in jupyter notebooks.
