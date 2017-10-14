package com.yet.another.utils.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListUtils {


    /**
     * Split a List of T into partition of n elements.
     * If the provided list is update, the result will not reflect the modification.
     *
     * @param list list to partition
     * @param partitionSize maximum size of each partition. the last partition may be smaller than partitionSize
     * @param <T>  Type of element of the list
     * @return a List of partition
     * @throws IllegalArgumentException if list is null or partitionSize <= 0 or list.size < partitionSize
     */
    public static <T> List<List<T>> partition(final List<T> list, final int partitionSize) {
        return partition((Supplier<List<T>>) ArrayList::new).apply(list, partitionSize);
    }

    /**
     * Split a List of T into partition of n elements.
     * If the provided list is update, the result will not reflect the modification.
     * <p>
     * <pre>{@code
     * partition(() -> new LinkedList<T>).apply(list,size)
     * }</pre>
     *
     * @param supplier A List Supplier for partition'List
     * @param <T>      Type of element
     * @return a BiFunction to split list in partition of n elements
     */
    public static <T> BiFunction<List<T>, Integer, List<List<T>>> partition(final Supplier<List<T>> supplier) {
        return (List<T> list, Integer partitionSize) -> {
            if (list == null) {
                throw new IllegalArgumentException("list must be not null");
            }
            if (partitionSize <= 0) {
                throw new IllegalArgumentException(String.format("partitionSize [%s] must be greater than 0", partitionSize));
            }
            if (list.size() < partitionSize) {
                throw new IllegalArgumentException(String.format("size of the list [%s] must be greater or equal to partitionSize [%s]", list.size(), partitionSize));
            }

            final int nbChunks = (list.size() / partitionSize) + (list.size() % partitionSize);

            return IntStream.range(0, nbChunks).boxed().map(chunkIndex -> {
                int from = chunkIndex * partitionSize;
                int to = Math.min(from + partitionSize, list.size());
                return copyList(list.subList(from, to), supplier);
            }).collect(Collectors.toList());
        };
    }


    private static <T> List<T> copyList(List<T> src, Supplier<List<T>> listSupplier) {
        final List<T> result = listSupplier.get();
        result.addAll(src);
        return result;
    }

    private ListUtils(){
    }
}
