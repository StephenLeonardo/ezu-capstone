from django.shortcuts import render
# from .serializers import AudioSerializer
from django.http import JsonResponse
from .data_tools import scaled_in, inv_scaled_ou
from .data_tools import audio_files_to_numpy, numpy_audio_to_matrix_spectrogram, matrix_spectrogram_to_numpy_audio, export_audio_file

from rest_framework.decorators import api_view
from rest_framework.response import Response

import soundfile as sf
import tensorflow as tf
from tensorflow.keras.models import model_from_json

physical_devices = tf.config.experimental.list_physical_devices('GPU')
if len(physical_devices) > 0:
   tf.config.experimental.set_memory_growth(physical_devices[0], True)

from capstone.settings import WEIGHTS_FOLDER

# Create your views here.


# Sample rate chosen to read audio
sample_rate = 8000
# Minimum duration of audio files to consider
min_duration = 1.0
# Our training data will be frame of slightly above 1 second
frame_length = 8064
# hop length for clean voice files separation (no overlap)
hop_length_frame = 8064
# hop length for noise files (we split noise into several windows)
hop_length_frame_noise = 5000

def callGoogleAPI():
    pass


def loadModel():
    path_weights = WEIGHTS_FOLDER
    # load json and create model
    json_file = open(path_weights+'model_unet.json', 'r')
    loaded_model_json = json_file.read()
    json_file.close()
    loaded_model = model_from_json(loaded_model_json)
    # load weights into new model
    loaded_model.load_weights(path_weights+'model_unet.h5')

    return loaded_model

loaded_model = loadModel()

@api_view(['GET'])
def getTranslation(request):
    filename = request.query_params.get('filename', None)

    # Url untuk cloud storage
    dirname = 'C:/Users/Steph/#Binus/#Bangkit/#Project/ezu-capstone/model/'

    audio = audio_files_to_numpy(dirname, [filename], sample_rate,
                             frame_length, hop_length_frame, min_duration)

    n_fft = 255
    hop_length_fft = 63
    dim_square_spec = int(n_fft / 2) + 1

    m_amp_db_audio,  m_pha_audio = numpy_audio_to_matrix_spectrogram(
    audio, dim_square_spec, n_fft, hop_length_fft)




    #global scaling to have distribution -1/1
    X_in = scaled_in(m_amp_db_audio)
    #Reshape for prediction
    X_in = X_in.reshape(X_in.shape[0],X_in.shape[1],X_in.shape[2],1)
    #Prediction using loaded network
    X_pred = loaded_model.predict(X_in)
    #Rescale back the noise model
    inv_sca_X_pred = inv_scaled_ou(X_pred)
    #Remove noise model from noisy speech
    X_denoise = m_amp_db_audio - inv_sca_X_pred[:,:,:,0]
    #Reconstruct audio from denoised spectrogram and phase
    audio_denoise_recons = matrix_spectrogram_to_numpy_audio(X_denoise, m_pha_audio, frame_length, hop_length_fft)




    output_file = (audio_denoise_recons.flatten() * 100)
    export_filename = 'test.wav'
    # url untuk export audio yg udah dijernihin ke cloud storage
    export_path = 'C:/Users/Steph/#Binus/#Bangkit/#Project/ezu-capstone/api/api/' + export_filename
    sf.write(export_path, output_file, sample_rate)

    callGoogleAPI()

    return Response({'status': 'Success!'})

    # export_audio_file(export_path, output_file, sample_rate)
