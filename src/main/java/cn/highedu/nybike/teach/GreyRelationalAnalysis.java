package cn.highedu.nybike.teach;

public class GreyRelationalAnalysis {
    //灰色关联分析
    // 数据标准化
    public static double[][] normalize(double[][] data) {
        double[][] normalizedData = new double[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                normalizedData[i][j] = (data[i][j] - minValue(data)) / (maxValue(data) - minValue(data));
            }
        }
        return normalizedData;
    }

    // 计算最大值
    private static double maxValue(double[][] data) {
        double max = data[0][0];
        for (double[] row : data) {
            for (double value : row) {
                if (value > max) {
                    max = value;
                }
            }
        }
        return max;
    }

    // 计算最小值
    private static double minValue(double[][] data) {
        double min = data[0][0];
        for (double[] row : data) {
            for (double value : row) {
                if (value < min) {
                    min = value;
                }
            }
        }
        return min;
    }

    // 计算关联度
    public static double[] calculateRelationalDegree(double[][] normalizedData) {
        double[] relationalDegrees = new double[normalizedData.length - 1];
        double[][] differenceSeries = new double[normalizedData.length - 1][normalizedData[0].length];
        for (int i = 1; i < normalizedData.length; i++) {
            for (int j = 0; j < normalizedData[i].length; j++) {
                differenceSeries[i - 1][j] = Math.abs(normalizedData[0][j] - normalizedData[i][j]);
            }
        }

        double maxDiff = maxValue(differenceSeries);
        double minDiff = minValue(differenceSeries);

        for (int i = 0; i < differenceSeries.length; i++) {
            double sum = 0;
            for (int j = 0; j < differenceSeries[i].length; j++) {
                sum += (minDiff + 0.5 * maxDiff) / (differenceSeries[i][j] + 0.5 * maxDiff);
            }
            relationalDegrees[i] = sum / differenceSeries[i].length;
        }

        return relationalDegrees;
    }

    public static void main(String[] args) {
        // 示例数据，第一行为参考序列，其余为比较序列
        double[][] data = {
                {3439, 4002, 4519, 4995, 5566},
                {341, 409, 556, 719, 903},
                {183, 196, 564, 598, 613},
                {3248, 3856, 6029, 7358, 8880},
                // 可以添加更多序列
        };

        double[][] normalizedData = normalize(data);
        double[] relationalDegrees = calculateRelationalDegree(normalizedData);

        for (int i = 0; i < relationalDegrees.length; i++) {
            System.out.println("序列 " + (i + 1) + " 的关联度: " + relationalDegrees[i]);
        }
    }
}

