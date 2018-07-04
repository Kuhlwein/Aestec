package aestec.util;

import java.util.Random;

public class PerlinNoise {
    private Random random;

    public PerlinNoise() {
        random = new Random();
    }

    public PerlinNoise(Random random) {
        this.random = random;
    }

    private float lerp(float x0, float x1, float a)
    {
        return x0 * (1 - a) + a * x1;
    }

    private float[][] getSmoothNoise(float[][] baseNoise, int octave)
    {
        int width = baseNoise.length;
        int height = baseNoise[0].length;

        float[][] smoothNoise = new float[width][height];

        int samplePeriod = 1 << octave; //bitwise power of two
        float sampleFrequency = 1.0f / samplePeriod;

        for (int i = 0; i < width; i++)
        {
            //calculate the horizontal sampling indices
            int sample_i0 = (i / samplePeriod) * samplePeriod;
            int sample_i1 = (sample_i0 + samplePeriod) % width;
            float horizontal_blend = (i - sample_i0) * sampleFrequency;

            for (int j = 0; j < height; j++)
            {
                //calculate the vertical sampling indices
                int sample_j0 = (j / samplePeriod) * samplePeriod;
                int sample_j1 = (sample_j0 + samplePeriod) % height;
                float vertical_blend = (j - sample_j0) * sampleFrequency;

                //blend the top two corners
                float top = lerp(baseNoise[sample_i0][sample_j0],
                        baseNoise[sample_i1][sample_j0], horizontal_blend);

                //blend the bottom two corners
                float bottom = lerp(baseNoise[sample_i0][sample_j1],
                        baseNoise[sample_i1][sample_j1], horizontal_blend);

                //final blend
                smoothNoise[i][j] = lerp(top, bottom, vertical_blend);
            }
        }

        return smoothNoise;
    }

    public float[][] getPerlinNoise(int xDim, int yDim, int octaveCount)
    {
        //Generate basenoise
        float[][] baseNoise = new float[xDim][yDim];
        for (int i = 0; i < xDim; i++)
        {
            for (int j = 0; j < yDim; j++)
            {
                baseNoise[i][j] = random.nextFloat() % 1;
            }
        }

        //Generate perlinnoise
        float[][][] smoothNoise = new float[octaveCount][][];
        for (int i = 0; i < octaveCount; i++)
        {
            smoothNoise[i] = getSmoothNoise(baseNoise, i);
        }


        float[][] fractalNoise = new float[xDim][yDim];
        float amplitude = 1.0f;
        float totalAmplitude = 0.0f;

        //blend noise
        for (int octave = octaveCount - 1; octave >= 0; octave--)
        {
            amplitude *= 0.5f;
            totalAmplitude += amplitude;

            for (int i = 0; i < xDim; i++)
            {
                for (int j = 0; j < yDim; j++)
                {
                    fractalNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
                }
            }
        }

        //normalisation
        for (int i = 0; i < xDim; i++)
        {
            for (int j = 0; j < yDim; j++)
            {
                fractalNoise[i][j] /= totalAmplitude;
            }
        }

        return fractalNoise;
    }
}
